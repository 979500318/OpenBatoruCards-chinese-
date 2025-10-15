package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class LRIGA_K2_MahomahoZudodoon extends Card {
    
    public LRIGA_K2_MahomahoZudodoon()
    {
        setImageSets("WXDi-P05-030");
        
        setOriginalName("まほまほ☆ずどどーん");
        setAltNames("マホマホズドドーン Mahomaho Zudodoon");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを４枚トラッシュに置く。"
        );
        
        setName("en", "Mahomaho☆Zudodon");
        setDescription("en",
                "@U: At the beginning of your attack phase, put the top four cards of your opponent's deck into their trash."
        );
        
        setName("en_fan", "Mahomaho☆Zudodoon");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, your opponent puts the top 4 cards of their deck into the trash."
        );
        
		setName("zh_simplified", "真帆帆☆哫咚咚");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从对战对手的牌组上面把4张牌放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAHOMAHO);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            millDeck(getOpponent(), 4);
        }
    }
}
