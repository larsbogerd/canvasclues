<script lang="ts">
    let {
        src,
        alt,
        fit = 'cover',
        backdrop = false,
    }: {
        src: string
        alt: string
        fit?: 'cover' | 'contain'
        backdrop?: boolean
    } = $props()

    // ARTIC renders IIIF sizes on demand, so a cold image can take ten seconds or more.
    let state = $state<'loading' | 'loaded' | 'failed'>('loading')

    $effect.pre(() => {
        void src
        state = 'loading'
    })
</script>

<div class="artwork">
    {#if state === 'loading'}
        <div class="skeleton" aria-hidden="true"></div>
    {:else if state === 'failed'}
        <div class="fallback">Afbeelding niet beschikbaar</div>
    {/if}
    {#if backdrop && state === 'loaded'}
        <img class="backdrop" {src} alt="" aria-hidden="true" />
    {/if}
    <img
        class="main"
        class:hidden={state !== 'loaded'}
        style:object-fit={fit}
        {src}
        {alt}
        draggable="false"
        onload={() => (state = 'loaded')}
        onerror={() => (state = 'failed')}
    />
</div>

<style>
    .artwork {
        position: relative;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }

    img {
        display: block;
        width: 100%;
        height: 100%;
    }

    .main {
        position: relative;
        transition: opacity 250ms ease;
    }

    .main.hidden {
        opacity: 0;
    }

    .backdrop {
        position: absolute;
        inset: 0;
        object-fit: cover;
        filter: blur(36px) saturate(1.15);
        transform: scale(1.15);
        opacity: 0.55;
        pointer-events: none;
    }

    .skeleton,
    .fallback {
        position: absolute;
        inset: 0;
        background-color: var(--color-secondary);
    }

    .skeleton {
        background: linear-gradient(
            100deg,
            var(--color-secondary) 40%,
            var(--color-bg-base) 50%,
            var(--color-secondary) 60%
        );
        background-size: 200% 100%;
        animation: shimmer 1.4s linear infinite;
    }

    .fallback {
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 12%;
        text-align: center;
        font-family: var(--font-secondary), sans-serif;
        font-size: 0.8rem;
        color: var(--text-tertiary);
    }

    @keyframes shimmer {
        from {
            background-position: 100% 0;
        }
        to {
            background-position: -100% 0;
        }
    }

    @media (prefers-reduced-motion: reduce) {
        .skeleton {
            animation: none;
        }
    }
</style>
