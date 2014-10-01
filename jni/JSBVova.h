#ifndef JSBVOVA
#define JSBVOVA
#include <string>
#include <map>
namespace JSBSim {
	class FGFDMExec;
}

class JSBModel;
class RoutePoint;

class JSBProvider {
	public: 
	JSBProvider(const std::string &plane);
	~JSBProvider();
	bool init();
	private:
	uint64_t	m_timestamp;		
	public:

protected:
	uint64_t m_objectId;
	std::string m_model;
	JSBModel* m_manager;
	JSBSim::FGFDMExec *m_fgFDM;	// JSBSim simulation executive
	double m_dt;
	bool m_climbDone;
	bool m_landingDone;
	bool m_gearsRaised;
	bool m_speedRaised;
	uint64_t m_activeWP;
	RoutePoint* m_currentRoutePoint;
    bool m_landingStarted;
	double m_checkPointDistance;
	double m_landingDistance;
};

class JSBProvidersManager
{
public:
	static	JSBProvidersManager &instance();
	JSBProvider* getProvider(uint64_t pointer);
	uint64_t createProvider(std::string &name);
private:
	JSBProvidersManager();
	~JSBProvidersManager();
	
	std::map<uint64_t,JSBProvider*> m_map;

};


#endif
