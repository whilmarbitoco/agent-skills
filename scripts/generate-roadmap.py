#!/usr/bin/env python3
"""
generate-roadmap.py — Generate and update the skill roadmap.

Usage:
    python3 generate-roadmap.py

Reads taxonomy.yaml and produces a human-readable ROADMAP.md
with completion progress, dependencies, and phase planning.
"""

import sys
import yaml
from pathlib import Path


def main():
    taxonomy_path = Path("../references/taxonomy.yaml")
    if not taxonomy_path.exists():
        print("taxonomy.yaml not found. Run from scripts/ directory.", file=sys.stderr)
        sys.exit(1)

    with open(taxonomy_path) as f:
        data = yaml.safe_load(f)

    lines = [
        "# Roadmap — Java 21 LTS Agent Skills",
        "",
        f"**Version:** {data.get('version', 'working')}  ",
        f"**Last Updated:** {data.get('last_updated', 'TBD')}",
        "",
        "## Domain Progress",
        "",
    ]

    total_skills = 0
    for domain_key, domain_info in data.get("domains", {}).items():
        skills = domain_info.get("skills", [])
        total_skills += len(skills)
        lines.append(f"### {domain_info.get('description', domain_key)}")
        lines.append("")
        lines.append("| ID | Title | Level | Status |")
        lines.append("|----|-------|-------|--------|")
        for skill in skills:
            lines.append(
                f"| `{skill['id']}` | {skill['title']} | {skill.get('level', '-')} | {skill.get('status', 'stub')} |"
            )
        lines.append("")

    lines.append("## Statistics")
    lines.append("")
    stats = data.get("stats", {})
    lines.append(f"- **Domains:** {stats.get('total_domains', len(data.get('domains', {})))}")
    lines.append(f"- **Total Skills:** {total_skills}")
    lines.append("")
    lines.append("## Version Plan")
    lines.append("")
    lines.append("| Version | Scope | Status |")
    lines.append("|---------|-------|--------|")
    lines.append("| v0.1.0 | Skill templates + schema + references | 🚧 in progress |")
    lines.append("| v0.2.0 | core-java domain (10 skills) | ⬜ planned |")
    lines.append("| v0.3.0 | architecture + patterns domains | ⬜ planned |")
    lines.append("| v0.4.0 | ui-javafx + ui-kickstartfx domains | ⬜ planned |")
    lines.append("| v0.5.0 | persistence + maven + testing domains | ⬜ planned |")
    lines.append("| v0.6.0 | packaging + performance + security domains | ⬜ planned |")
    lines.append("| v0.7.0 | pos-domain + reporting domains | ⬜ planned |")
    lines.append("| v0.8.0 | Reference applications (7 demos) | ⬜ planned |")
    lines.append("| v0.9.0 | AI/Agent optimization pass (all skills) | ⬜ planned |")
    lines.append("| v1.0.0 | All skills complete + published | ⬜ planned |")
    lines.append("")
    lines.append("## Future Expansion (v2.0+)")
    lines.append("")
    lines.append("- Spring Boot integration module")
    lines.append("- REST sync server (offline-first → online sync)")
    lines.append("- WebSocket real-time inventory sync")
    lines.append("- Multi-branch / multi-store support")
    lines.append("- Hardware integrations (fiscal printers, scales)")
    lines.append("- Linux deployment playbooks")
    lines.append("- Plugin architecture")
    lines.append("")

    output = "\n".join(lines)
    Path("../ROADMAP.md").write_text(output, encoding="utf-8")
    print(output)
    print(f"\n✅ ROADMAP.md generated ({len(output)} bytes, {total_skills} skills)")


if __name__ == "__main__":
    main()
