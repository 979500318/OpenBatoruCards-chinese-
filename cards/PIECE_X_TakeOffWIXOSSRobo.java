package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.events.EventCoin;
public final class PIECE_X_TakeOffWIXOSSRobo extends Card {
    
    public PIECE_X_TakeOffWIXOSSRobo()
    {
        setImageSets("WXDi-P07-006");
        
        setOriginalName("発進！WIXOSSロボ");
        setAltNames("ハッシンウイクロスロボ Hasshin Uikurosu Robo");
        setDescription("jp",
                "このゲームの間にあなたが#Cを得ていない場合、#C #C #C #C #Cを得る。このゲームの間、あなたは#Cを得られない。"
        );
        
        setName("en", "Take Off! WIXOSS Robo!");
        setDescription("en",
                "If you do not gain #C for the duration of the game, gain #C #C #C #C #C. You cannot gain #C for the duration of the game."
        );
        
        setName("en_fan", "Take Off! WIXOSS Robo");
        setDescription("en_fan",
                "If you haven't gained #C this game, gain #C #C #C #C #C. This game, you can't gain #C."
        );
        
		setName("zh_simplified", "发进！WIXOSS机器");
        setDescription("zh_simplified", 
                "这场游戏期间，你没有得到过币的场合，得到#C #C #C #C #C。这场游戏期间，你不能得到:币。\n"
        );

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            if(GameLog.getGameRecordsCount((event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() > 0)) == 0)
            {
                gainCoins(5);
                
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_GAIN_COINS, getOwner(), ChronoDuration.permanent(), data -> RuleCheckState.BLOCK);
            }
        }
    }
}
