# Multi-source artworks migration (2026-10-12)
Added for older databases. Run this on production before upgrading from `sha-695fe8f` or older to `sha-fcdf7e7` (#18) or newer.

**Change:** artworks no longer use ARTIC's `image_id` as their primary key. The existing ids were copied into a new `external_image_id`, a `source` column was added to `artwork` and `game` (backfilled with `artic`), and two ARTIC image ids that had been re-issued were updated.