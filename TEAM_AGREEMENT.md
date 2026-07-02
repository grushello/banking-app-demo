# Team Agreement

## Coding Standards & Work Conventions

- **Language:** Java 17+ using Spring Boot best practices.
- **Code Style:** Follow standard Java naming conventions (camelCase for variables/methods, PascalCase for classes).
- **Format:** Use automated IDE formatting before committing.
- **Branching:** Do all work on your own branches cut from `dev`.
- **Scope:** One task == one branch.
- **Sync:** When starting work, check if your local repo is up to date with `origin`.
- **Verification:** Verify that the application builds and all tests pass before creating pull requests.

## Testing Policy

- Every new feature or bug fix **must** be accompanied by unit tests.
- Aim for high coverage in service and controller layers.
- Use `JUnit 5` and `Mockito` for testing.

## Git Workflow

### Main Branches

- `main` — production-ready most stable version.
- `dev` — feature integration branch.

### Branch Naming Convention

Use the following branch naming pattern:
- `feature/task-name` (e.g., `feature/account-creation`)
- `bugfix/issue-name` (e.g., `bugfix/fix-transaction-overlap`)
- `docs/topic-name`
- `refactor/component-name`

#### Allowed Prefixes
- `feat` — a new feature or functionality
- `fix` — a bug fix
- `docs` — changes to documentation
- `test` — test-related work
- `build` — project setup, build tools, or dependency changes
- `ci` — CI/CD changes
- `refactor` — code restructuring that neither fixes a bug nor adds a feature
- `chore` — maintenance work or miscellaneous tasks

### Pull Request Rules

#### Rules
- Do not commit directly to `main`.
- Do not commit directly to `develop` unless the team explicitly agrees.
- Create a dedicated branch for each task or issue.
- Keep PRs focused and reasonably small.
- Use a clear PR title.
- Ensure the branch is up to date before requesting review.
- Verify that the application builds and the relevant checks pass before merging.
- Get at least two approvals from other team members before merging.

- **Approvals:** Every Pull Request requires at least **2 approvals** from other team members before it can be merged.
- **Reviewers:** Reviewers can be anyone from the team (no need for tagging).
- **Merge Conflicts:** The author of the Pull Request is responsible for resolving any merge conflicts.
- **PR Linking Rule:** Every Pull Request **must** be linked to its corresponding GitHub Issue. Use keywords like "Resolves #1" or "Closes #1" in the PR description, or use the GitHub UI to link it, so the project board updates automatically.
- **Branch Cleanup Rule:** Feature branches **must** be deleted immediately after a PR is successfully merged into `dev` or `main` to keep the branch list clean.

## Communication Expectations

- **Commit Messages:** Use clear and consistent messages. Format: `type: short description` (e.g., `feat: add account controller`).
- **Architectural Changes:** Discuss major changes in dedicated communication channels before implementation.
- **Issue Tracking:** Keep GitHub Issues updated with progress (To Do, In Progress, Done).
