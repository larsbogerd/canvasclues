<div class="label" aria-label="Vervalst">
    {#each { length: 6 }, i (i)}
        <span class="block"></span>
        <span class="text" aria-hidden="true">vervalst</span>
    {/each}
</div>

<style>
    .label {
        display: flex;
        align-items: center;
        position: absolute;
        width: max-content;
        height: 24px;
        background-color: var(--color-tertiary-active);
        z-index: 1;
        bottom: 30%;
        right: -30%;
        transform: rotate(6deg);
        pointer-events: none;
    }

    .block {
        width: 60px;
        height: 100%;
        background-color: var(--icon-primary);
    }

    .text {
        margin: 0 4px;
        color: var(--assasin-red);
        font-weight: 700;
        font-family: var(--font-secondary), sans-serif;
        font-size: 1rem;
        text-transform: uppercase;
        letter-spacing: 0.02em;
    }
</style>
