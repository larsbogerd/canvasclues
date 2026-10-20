<script lang="ts">
    import '#lib/styles/colors.css'
    import '#lib/styles/fonts.css'
    import '#lib/styles/modal.css'
    import type { LayoutProps } from './$types'

    let { children }: LayoutProps = $props()
</script>

{@render children()}
