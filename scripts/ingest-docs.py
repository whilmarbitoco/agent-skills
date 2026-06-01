#!/usr/bin/env python3
"""
ingest-docs.py — Fetch and clean official documentation pages.

Usage:
    python3 ingest-docs.py <url> <output_path>

Fetches a URL, strips boilerplate, and saves clean Markdown with YAML frontmatter.
"""

import sys
import re
import html
import json
import urllib.request
from datetime import datetime
from pathlib import Path


def fetch(url: str) -> str:
    """Fetch HTML from URL."""
    req = urllib.request.Request(
        url,
        headers={
            "User-Agent": "Mozilla/5.0 (Java21-SkillBot/1.0)",
            "Accept": "text/html,application/xhtml+xml",
        },
    )
    with urllib.request.urlopen(req, timeout=30) as resp:
        return resp.read().decode("utf-8", errors="replace")


def clean(raw_html: str) -> str:
    """Strip HTML tags, scripts, styles, and normalize whitespace."""
    text = raw_html
    text = re.sub(r"<script[^>]*>.*?</script>", "", text, flags=re.DOTALL)
    text = re.sub(r"<style[^>]*>.*?</style>", "", text, flags=re.DOTALL)
    text = re.sub(r"<!--.*?-->", "", text, flags=re.DOTALL)

    # Preserve code blocks
    code_blocks = {}
    def save_code(m):
        key = f"__CODE_{len(code_blocks)}__"
        code_blocks[key] = html.unescape(m.group(1))
        return key
    text = re.sub(r"<pre[^>]*>(.*?)</pre>", save_code, text, flags=re.DOTALL)

    # Convert headers
    for level in range(1, 7):
        hashes = "#" * level
        text = re.sub(
            rf"<h{level}[^>]*>(.*?)</h{level}>",
            lambda m, h=hashes: f"\n\n{h} {html.unescape(m.group(1).strip())}\n\n",
            text,
            flags=re.DOTALL,
        )

    # Convert lists
    text = re.sub(r"<li[^>]*>(.*?)</li>", lambda m: f"- {html.unescape(m.group(1).strip())}", text, flags=re.DOTALL)
    text = re.sub(r"</?(?:ul|ol)[^>]*>", "\n", text)

    # Convert paragraphs
    text = re.sub(r"</?(?:p|div|section|article)[^>]*>", "\n\n", text)

    # Inline code
    text = re.sub(r"<code[^>]*>(.*?)</code>", lambda m: f"`{html.unescape(m.group(1).strip())}`", text)

    # Links
    text = re.sub(
        r'<a[^>]*href="([^"]*)"[^>]*>(.*?)</a>',
        lambda m: f"[{html.unescape(m.group(2).strip())}]({m.group(1)})",
        text,
        flags=re.DOTALL,
    )

    # Strip remaining tags
    text = re.sub(r"<[^>]+>", " ", text)
    text = html.unescape(text)

    # Restore code blocks
    for key, code in code_blocks.items():
        text = text.replace(key, f"\n```\n{code}\n```\n")

    # Normalize whitespace
    text = re.sub(r"[ \t]+", " ", text)
    text = re.sub(r"\n{3,}", "\n\n", text)
    text = text.strip()

    return text


def make_frontmatter(url: str, tier: str = "1") -> str:
    now = datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%SZ")
    return (
        f"---\n"
        f"source: {url}\n"
        f"tier: {tier}\n"
        f"fetched_at: {now}\n"
        f"ingestor: ingest-docs.py\n"
        f"---\n\n"
    )


def main():
    if len(sys.argv) < 3:
        print(f"Usage: {sys.argv[0]} <url> <output_path> [tier]")
        sys.exit(1)

    url = sys.argv[1]
    output_path = Path(sys.argv[2])
    tier = sys.argv[3] if len(sys.argv) > 3 else "1"

    print(f"Fetching: {url}")
    raw = fetch(url)

    print(f"Cleaning... ({len(raw)} bytes raw)")
    clean_text = clean(raw)

    output_path.parent.mkdir(parents=True, exist_ok=True)
    content = make_frontmatter(url, tier) + clean_text
    output_path.write_text(content, encoding="utf-8")

    print(f"Written: {output_path} ({len(content)} bytes)")
    print(f"Lines: {content.count(chr(10))}")


if __name__ == "__main__":
    main()
