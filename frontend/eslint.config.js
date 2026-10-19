import js from '@eslint/js'
import globals from 'globals'
import svelte from 'eslint-plugin-svelte'
import tseslint from 'typescript-eslint'
import { defineConfig, globalIgnores } from 'eslint/config'

export default defineConfig([
    globalIgnores(['build', '.svelte-kit']),
    {
        files: ['**/*.{ts,svelte}'],
        extends: [
            js.configs.recommended,
            tseslint.configs.recommendedTypeChecked,
            svelte.configs.recommended,
            svelte.configs.prettier,
        ],
        languageOptions: {
            globals: globals.browser,
            parserOptions: {
                projectService: true,
                tsconfigRootDir: import.meta.dirname,
                extraFileExtensions: ['.svelte'],
            },
        },
    },
    {
        files: ['**/*.svelte', '**/*.svelte.ts'],
        languageOptions: {
            parserOptions: { parser: tseslint.parser },
        },
    },
])
