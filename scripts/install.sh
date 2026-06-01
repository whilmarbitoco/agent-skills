#!/usr/bin/env bash
# install.sh — Install Java 21 agent skills for AI coding agents
#
# Usage:
#   ./install.sh                    # Install to current project (./.agents/skills/)
#   ./install.sh --global           # Install to ~/.agents/skills/ (all projects)
#   ./install.sh --cursor           # Install to .cursor/rules/ (Cursor editor)
#   ./install.sh --claude           # Install to .claude/skills/ (Claude Code)
#   ./install.sh --list             # List available skills
#
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAVA_DIR="$SCRIPT_DIR/java"
INSTALL_DIR=""
MODE="project"

# Parse args
while [[ $# -gt 0 ]]; do
    case "$1" in
        --global)  INSTALL_DIR="$HOME/.agents/skills"; shift ;;
        --cursor)  MODE="cursor"; INSTALL_DIR=".cursor/skills"; shift ;;
        --claude)  MODE="claude"; INSTALL_DIR=".claude/skills"; shift ;;
        --list)    MODE="list"; shift ;;
        -h|--help)
            sed -n '2,9p' "$0" | sed 's/^# //'
            exit 0 ;;
        *) echo "Unknown option: $1"; exit 1 ;;
    esac
done

# List mode
if [[ "$MODE" == "list" ]]; then
    echo "Available skills:"
    echo ""
    for skill_dir in "$JAVA_DIR"/*/; do
        skill=$(basename "$skill_dir")
        desc=$(grep '^description:' "$skill_dir/SKILL.md" 2>/dev/null | head -1 | sed 's/description: >$//' | sed 's/^description: //' | head -c 80)
        printf "  %-30s %s\n" "$skill" "$desc"
    done
    echo ""
    echo "Install with: ./install.sh [--global|--cursor|--claude]"
    exit 0
fi

# Default install dir
if [[ -z "$INSTALL_DIR" ]]; then
    INSTALL_DIR="./.agents/skills"
fi

echo "Installing Java 21 agent skills..."
echo "  Source: $JAVA_DIR"
echo "  Target: $INSTALL_DIR"
echo ""

# Create directory
mkdir -p "$INSTALL_DIR"

# Copy skills
count=0
for skill_dir in "$JAVA_DIR"/*/; do
    skill=$(basename "$skill_dir")
    target="$INSTALL_DIR/$skill"
    rm -rf "$target"
    cp -r "$skill_dir" "$target"
    count=$((count + 1))
    echo "  ✓ $skill"
done

echo ""
echo "Installed $count skills to $INSTALL_DIR"
echo ""

case "$MODE" in
    cursor)
        echo "Cursor: Skills are available in .cursor/skills/"
        echo "Cursor will auto-discover them when editing .java files."
        ;;
    claude)
        echo "Claude Code: Use /<skill-name> to invoke a skill"
        echo "Or let Claude auto-detect based on your task."
        ;;
    *)
        echo "Agent-agnostic: Point your agent's skills directory at $INSTALL_DIR"
        echo "Each SKILL.md follows the Agent Skills open standard (agentskills.io)"
        ;;
esac
