import Footer from "./Footer"
import Navbar from "./Navbar"

//General website layout 
const Layout = ({ children }) => {
  return (
    <div className="content">
      <Navbar />
      { children }
      <Footer />
    </div>
  );
}
 
export default Layout;