import {Link} from "react-router-dom";

const NotFound = () => {
    return (
        <>
            <div className="flex justify-center flex-col gap-5" style={{height:'100%', alignItems: 'center'}}>
                <h1 className="text-2xl">Nothing to see here!</h1>
                <Link to="/">
                    <button
                        className="w-1/2 px-5 py-2 text-sm tracking-wide text-white transition-colors duration-200 bg-gray-500 rounded-lg shrink-0 sm:w-auto hover:bg-gray-700 dark:hover:bg-gray-500 dark:bg-gray-600"
                        style={{cursor: 'pointer'}}>
                        Take me home
                    </button>
                </Link>
            </div>
        </>
    )
}

export default NotFound;