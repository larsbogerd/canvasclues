<script lang="ts">
    import type { HTMLButtonAttributes } from 'svelte/elements'

    let {
        variant = 'primary',
        children,
        ...rest
    }: HTMLButtonAttributes & { variant?: 'primary' | 'secondary' } = $props()
</script>

<button type="button" class={variant} {...rest}>{@render children?.()}</button>

<style>
    button {
        font-family: var(--font-secondary), sans-serif;
        font-size: 1.2rem;
        font-weight: 600;
        padding: 0.6rem 4rem;
        border-radius: 20px;
        cursor: pointer;
        transition:
            border-color 180ms ease,
            box-shadow 180ms ease,
            opacity 180ms ease;
    }

    .primary {
        background-color: var(--color-primary);
        color: var(--color-bg-base);
        border: none;
        box-shadow: 0 10px 20px
            color-mix(in srgb, var(--color-primary) 28%, transparent);
    }

    .primary:hover {
        box-shadow: 0 14px 24px
            color-mix(in srgb, var(--color-primary) 38%, transparent);
    }

    .secondary {
        background-color: transparent;
        color: var(--text-primary);
        border: 2px solid var(--button-border);
        box-shadow: 0 14px 28px var(--tertiary-shadow);
    }

    .secondary:hover {
        border-color: var(--color-primary);
        box-shadow: 0 10px 24px
            color-mix(in srgb, var(--color-primary) 22%, transparent);
    }
</style>
