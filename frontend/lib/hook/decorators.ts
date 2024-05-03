
// workaround for static methods not being able to define in interfaces
export default function staticImplement<T>() {
    return <U extends T>(constructor: U) => { constructor };
}