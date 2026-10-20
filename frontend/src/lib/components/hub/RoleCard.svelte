<script lang="ts">
    import type { Snippet } from 'svelte'

    let {
        eyebrow,
        role,
        description,
        image,
        onclick,
        children,
    }: {
        eyebrow: string
        role: string
        description: string
        image: string
        onclick: () => void
        children?: Snippet
    } = $props()
</script>

<!-- A div, not a button: the difficulty picker inside is interactive too, and buttons can't nest. -->
<div
    class="card"
    role="button"
    tabindex="0"
    {onclick}
    onkeydown={(event) => {
        if (event.target === event.currentTarget && event.key === 'Enter')
            onclick()
    }}
>
    <span class="text">
        <span class="eyebrow">{eyebrow}</span>
        <span class="role">{role}</span>
        <span class="description">{description}</span>
        {@render children?.()}
    </span>
    <span class="icon">
        <img src={image} alt="" />
    </span>
</div>

<style>
    .card {
        position: relative;
        display: flex;
        align-items: stretch;
        overflow: hidden;
        background: var(--button-gradient);
        border: 2px solid var(--button-border);
        border-radius: 28px;
        padding: 2rem 2.25rem;
        margin: 10px;
        width: min(760px, 42vw);
        min-height: 310px;
        box-sizing: border-box;
        cursor: pointer;
        outline: none;
        box-shadow: 0 14px 28px var(--secondary-shadow);
        transition:
            border-color 180ms ease,
            box-shadow 180ms ease,
            transform 180ms ease;
    }

    .card:hover,
    .card:focus-visible {
        border-color: var(--color-primary);
        box-shadow: 0 10px 24px
            color-mix(in srgb, var(--color-primary) 22%, transparent);
        transform: translateY(-2px);
    }

    .text {
        display: flex;
        flex-direction: column;
        justify-content: center;
        gap: 0.75rem;
        width: min(60%, 360px);
    }

    .eyebrow {
        font-size: 0.82rem;
        letter-spacing: 0.18em;
        text-transform: uppercase;
        font-family: var(--font-secondary), sans-serif;
        color: var(--text-eyebrow);
        font-weight: 700;
    }

    .role {
        font-size: 3rem;
        font-weight: 500;
        font-family: var(--font-main), serif;
        line-height: 0.95;
        color: var(--text-primary);
    }

    .description {
        font-size: 1.05rem;
        font-family: var(--font-secondary), sans-serif;
        line-height: 1.45;
        max-width: 32ch;
        color: var(--text-secondary);
    }

    .icon {
        position: absolute;
        right: 2rem;
        top: 50%;
        transform: translateY(-50%);
        width: 42%;
        height: 78%;
        display: flex;
        align-items: center;
        justify-content: center;
        pointer-events: none;
    }

    img {
        max-height: 100%;
        max-width: 100%;
        opacity: 0.22;
        object-fit: contain;
    }

    @media (max-width: 1100px) {
        .card {
            width: min(760px, 88vw);
        }
    }

    @media (max-width: 760px) {
        .card {
            min-height: 260px;
            padding: 1.5rem;
        }

        .text {
            width: 66%;
        }

        .role {
            font-size: 2.35rem;
        }

        .description {
            font-size: 0.98rem;
        }
    }

    @media (prefers-reduced-motion: reduce) {
        .card {
            transition: none;
        }
    }
</style>
