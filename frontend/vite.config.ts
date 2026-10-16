import adapter from '@sveltejs/adapter-static';
import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [
		sveltekit({
			compilerOptions: {
				// Force runes mode for the project, except for libraries. Can be removed in svelte 6.
				runes: ({ filename }) =>
					filename.split(/[/\\]/).includes('node_modules') ? undefined : true
			},
			adapter: adapter({ fallback: 'index.html' })
		})
	],
	server: {
		port: 5174,
		strictPort: true,
		proxy: {
			'/api': {
				target: 'http://localhost:8082',
				changeOrigin: true,
				secure: false
			},
			// Mirrors the /img/<source>/ blocks in nginx.conf so artwork images work the
			// same way in dev as in Docker.
			'/img/artic': {
				target: 'https://www.artic.edu',
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/img\/artic/, '/iiif/2'),
				headers: {
					Referer: 'https://www.artic.edu/'
				}
			},
			'/img/met': {
				target: 'https://images.metmuseum.org',
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/img\/met/, '')
			},
			'/img/cma': {
				target: 'https://openaccess-cdn.clevelandart.org',
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/img\/cma/, '')
			}
		}
	}
});
