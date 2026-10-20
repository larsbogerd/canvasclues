<script lang="ts">
    import { Tween } from 'svelte/motion'
    import { cubicOut } from 'svelte/easing'

    let { score }: { score: number } = $props()

    const shown = Tween.of(() => score, { duration: 400, easing: cubicOut })
</script>

<div class="score-box">
    <span class="label">Score</span>
    <span class="value">{Math.round(shown.current)}</span>
</div>

<style>
    .score-box {
        display: flex;
        justify-content: center;
        align-items: baseline;
        gap: 10px;
        width: 100%;
        padding: 12px;
        border-radius: 12px;
        background-color: var(--color-bg-base);
        font-family: var(--font-secondary), sans-serif;
        font-size: clamp(1.4rem, 2vw, 2rem);
        font-weight: 600;
        line-height: 1.2;
    }

    .value {
        color: var(--color-primary);
        font-variant-numeric: tabular-nums;
    }
</style>
