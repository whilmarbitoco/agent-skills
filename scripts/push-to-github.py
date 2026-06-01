#!/usr/bin/env python3
"""Push the entire agent-skills directory tree to GitHub."""
import json, os, sys, base64, urllib.request, urllib.error
from pathlib import Path

TOKEN_PATH="/workspace/.secrets/github_token"
OWNER="whilmarbitoco"
REPO="agent-skills"
BRANCH="main"
API="https://api.github.com"

with open(TOKEN_PATH) as f:
    TOKEN=f.read().strip()

LOCAL=Path(__file__).parent.parent

IGNORE_DIRS={".git",".venv","__pycache__",".pytest_cache","node_modules"}
IGNORE_EXTS={".pyc",".class",".jar"}
IGNORE_FILES={".DS_Store","Thumbs.db"}


def api(method, endpoint, data=None):
    url=API+"/"+endpoint
    body=json.dumps(data).encode() if data else None
    headers={
        "Authorization":"Bearer "+TOKEN,
        "Accept":"application/vnd.github+json",
        "Content-Type":"application/json",
    }
    req=urllib.request.Request(url,data=body,headers=headers,method=method)
    try:
        with urllib.request.urlopen(req) as resp:
            raw=resp.read()
            return resp.status,json.loads(raw) if raw else None
    except urllib.error.HTTPError as e:
        err_body=e.read()
        try:
            err=json.loads(err_body)
        except Exception:
            err={"message":err_body.decode("utf-8",errors="replace")}
        return e.code,err


def put_file(rel,content,msg):
    enc=base64.b64encode(content).decode("ascii")
    ep="repos/"+OWNER+"/"+REPO+"/contents/"+rel
    st,ex=api("GET",ep)
    data={"message":msg,"content":enc,"branch":BRANCH}
    if st==200 and isinstance(ex,dict):
        data["sha"]=ex["sha"]
        action="Update"
    else:
        action="Create"
    st,r=api("PUT",ep,data=data)
    if st in (200,201):
        sha=r.get("content",{}).get("sha","?")[:12]
        print("  "+action+": "+rel+" ("+sha+")")
        return True
    else:
        print("  FAIL: "+rel+" - "+str(r.get("message",r)))
        return False


def should_ignore(name,is_dir):
    if name in IGNORE_FILES:
        return True
    if is_dir and name in IGNORE_DIRS:
        return True
    if not is_dir:
        ext=os.path.splitext(name)[1]
        if ext in IGNORE_EXTS:
            return True
    return False


def walk_and_push():
    total=0;success=0;failed=0;skipped=0
    for root,dirs,files in os.walk(LOCAL):
        dirs[:]=[d for d in dirs if not should_ignore(d,True)]
        for fname in files:
            if should_ignore(fname,False):
                skipped+=1;continue
            fp=Path(root)/fname
            rel=str(fp.relative_to(LOCAL))
            try:
                content=fp.read_bytes()
                content.decode("utf-8")
            except UnicodeDecodeError:
                print("  Skip: "+rel)
                skipped+=1;continue
            total+=1
            if put_file(rel,content,"Add "+rel):
                success+=1
            else:
                failed+=1
    return total,success,failed,skipped


print("Pushing to "+OWNER+"/"+REPO+" ("+BRANCH+")...")
print("Local: "+str(LOCAL))
print()

# Priority files
priority=[
    "README.md",
    "templates/skill-schema.yaml",
    "templates/SKILL-TEMPLATE.md",
    "references/canonical-stack.yaml",
    "references/taxonomy.yaml",
    "references/coding-standards.md",
    "references/canonical-project-structure.md",
    "scripts/ingest-docs.py",
    "scripts/generate_skills.py",
    "scripts/generate-roadmap.py",
]

for pf in priority:
    fp=LOCAL/pf
    if fp.exists():
        put_file(pf,fp.read_bytes(),"Add "+pf)

gitignore="""# Java
*.class
*.jar
*.war
target/
.idea/
*.iml

# Python
__pycache__/
*.py[cod]
.venv/

# OS
.DS_Store
Thumbs.db

# Config
.env
*.log
"""
put_file(".gitignore",gitignore.encode(),"Add .gitignore")

print()
print("Pushing all skill files:")
total,success,failed,skipped=walk_and_push()

print()
print("Done: "+str(success)+"/"+str(total)+" files pushed")
if failed:
    print("Failed: "+str(failed))
print("Skip: "+str(skipped))
print("Repo: https://github.com/"+OWNER+"/"+REPO)
