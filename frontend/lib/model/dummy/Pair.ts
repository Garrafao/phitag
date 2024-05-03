export default class Pair<T, U> {
    public left: T;
    public right: U;

    constructor(left: T, right: U) {
        this.left = left;
        this.right = right;
    }
}