import Link from 'next/link';

//Top navbar
const Navbar = () => {
  return (
    <nav>
      <div className="logo">
        <h1>HFDB</h1>
      </div>
      <Link href="/">Home </Link>
      <Link href="/search/"> Search</Link>
      <Link href="/about"> About </Link>
    </nav>
);
}
 
export default Navbar;