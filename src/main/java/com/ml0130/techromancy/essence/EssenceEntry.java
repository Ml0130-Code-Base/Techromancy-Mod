package com.ml0130.techromancy.essence;

/**
 * One essence in an item/block's composition.
 *
 * @param essence       the essence
 * @param amount        how much of it
 * @param requiresStrip if true, this essence is hidden until the item is processed at the Essence Striper
 *                      ("deep"); if false it is a "surface" essence revealed just by scanning/identifying
 */
public record EssenceEntry(Essence essence, int amount, boolean requiresStrip) {
}
