import vue from 'eslint-plugin-vue'
import importPlugin from 'eslint-plugin-import'
import path from 'path'
import eslintConfigPrettier from 'eslint-config-prettier'

const vue3Recommended = vue.configs['flat/recommended']

export default [
    ...vue3Recommended,
    eslintConfigPrettier,
    {
        files: ['**/*.vue', '**/*.js'],
        languageOptions: {
            ecmaVersion: 'latest',
            sourceType: 'module',
        },
        plugins: {
            import: importPlugin,
        },
        settings: {
            'import/resolver': {
                alias: {
                    map: [['@', path.resolve('./src')]],
                    extensions: ['.js', '.vue'],
                },
                node: {
                    extensions: ['.js', '.vue'],
                },
            },
        },
        rules: {
            'import/no-unresolved': 'error',
        },
    },
]
