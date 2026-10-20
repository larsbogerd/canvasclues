<script lang="ts">
    let {
        state,
        title,
        message,
        onretry,
    }: {
        state: 'loading' | 'error'
        title: string
        message: string
        onretry: () => void
    } = $props()
</script>

<!-- Stands in for the board at the same size, so the layout doesn't collapse around it. -->
<div class="status" aria-busy={state === 'loading'}>
    <div class="tiles" aria-hidden="true">
        {#each { length: 16 }, i (i)}
            <div class="tile" class:shimmer={state === 'loading'}></div>
        {/each}
    </div>

    {#if state === 'error'}
        <div class="error" role="alert">
            <p class="title">{title}</p>
            <p class="message">{message}</p>
            <button type="button" onclick={onretry}>Opnieuw proberen</button>
        </div>
    {/if}
</div>

<style>
    .status {
        position: relative;
        width: 100%;
    }

    .tiles {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        gap: 16px;
    }

    .tile {
        aspect-ratio: 1;
        border-radius: 15%;
        background-color: var(--color-secondary);
        opacity: 0.7;
    }

    .shimmer {
        background: linear-gradient(
            100deg,
            var(--color-secondary) 40%,
            var(--color-bg-base) 50%,
            var(--color-secondary) 60%
        );
        background-size: 200% 100%;
        animation: shimmer 1.4s linear infinite;
    }

    @keyframes shimmer {
        from {
            background-position: 100% 0;
        }
        to {
            background-position: -100% 0;
        }
    }

    .error {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: min(80%, 360px);
        padding: 1.5rem;
        border-radius: 16px;
        background-color: var(--color-secondary);
        box-shadow: 0 14px 28px var(--secondary-shadow);
        text-align: center;
    }

    .title {
        margin: 0 0 0.5rem;
        font-family: var(--font-main), serif;
        font-size: 1.25rem;
        color: var(--text-primary);
    }

    .message {
        margin: 0 0 1.25rem;
        font-family: var(--font-secondary), sans-serif;
        font-size: 0.9rem;
        line-height: 1.45;
        color: var(--text-secondary);
    }

    button {
        font-family: var(--font-secondary), sans-serif;
        font-weight: 600;
        padding: 0.6rem 1.25rem;
        border: none;
        border-radius: 999px;
        background-color: var(--color-primary);
        color: var(--color-secondary);
        cursor: pointer;
    }

    @media (prefers-reduced-motion: reduce) {
        .shimmer {
            animation: none;
        }
    }
</style>
