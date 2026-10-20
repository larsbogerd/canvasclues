<script lang="ts">
    import { resolve } from '$app/paths'

    let { phase }: { phase?: string } = $props()
</script>

<header>
    <a class="logo" href={resolve('/')}>
        <svg
            width="32"
            height="32"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
            aria-hidden="true"
        >
            <path
                d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10c1.1 0 2-.9 2-2 0-.53-.21-1.04-.59-1.41-.38-.38-.59-.88-.59-1.41 0-1.1.9-2 2-2h1.46c2.83 0 5.13-2.3 5.13-5.13C23.41 6.82 18.28 2 12 2z"
            />
            <circle cx="7.5" cy="10.5" r="1.5" fill="currentColor" />
            <circle cx="10.5" cy="6.5" r="1.5" fill="currentColor" />
            <circle cx="15.5" cy="7.5" r="1.5" fill="currentColor" />
        </svg>
        <span>Canvas Clues</span>
    </a>
    <p class="role">{phase}</p>
    <div class="right"></div>
</header>

<style>
    header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 10vh;
        min-height: 56px;
        padding: 0 2vw;
        border-bottom: 1px solid var(--button-border);
    }

    .logo {
        border-radius: 20px;
        color: var(--color-bg-base);
        display: flex;
        align-items: center;
        gap: 1rem;
        background-color: var(--color-primary);
        padding: 10px 20px;
        text-decoration: none;
        transition: box-shadow 180ms ease;
        transform: scale(clamp(0.6, 1.2vh, 1));
        transform-origin: left center;
    }

    .logo:hover {
        box-shadow: 0 10px 24px
            color-mix(in srgb, var(--color-primary) 22%, transparent);
    }

    .logo span {
        text-transform: uppercase;
        font-family: var(--font-display), sans-serif;
        font-weight: 700;
        letter-spacing: 2px;
        font-size: 24px;
    }

    .role {
        font-family: var(--font-main), sans-serif;
        font-size: clamp(1.5rem, 2.8vw, 2.4rem);
        font-weight: 500;
        color: var(--text-primary);
    }

    .right {
        width: 10%;
    }
</style>
