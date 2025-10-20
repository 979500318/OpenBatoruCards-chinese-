package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_K3_CodeAncientsPhalaris extends Card {
    
    public SIGNI_K3_CodeAncientsPhalaris()
    {
        setImageSets("WXDi-P02-046", "SPDi02-16", Mask.IGNORE+"WXDi-EXD01-026");
        
        setOriginalName("コードアンシエンツ　ファラリス");
        setAltNames("コードアンシエンツファラリス Koodo Anshientsu Fararisu");
        setDescription("jp",
                "=H ルリグ２体\n\n" +
                "@U：このシグニがバトルによって対戦相手のシグニをバニッシュしたとき、対戦相手のデッキの上からカードを１０枚トラッシュに置く。"
        );
        
        setName("en", "Phalaris, Code: Ancients");
        setDescription("en",
                "=H Two LRIG\n\n" +
                "@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field through battle, put the top ten cards of that player's deck into their trash."
        );
        
        setName("en_fan", "Code Ancients Phalaris");
        setDescription("en_fan",
                "=H 2 LRIGs\n\n" +
                "@U: Whenever this SIGNI banishes an opponent's SIGNI in battle, put the top 10 cards of your opponent's deck into the trash."
        );
        
		setName("zh_simplified", "古神代号 法拉里斯牛");
        setDescription("zh_simplified", 
                "=H分身2只（当这只精灵出场时，如果不把你的竖直状态的分身2只横置，那么将此牌横置）\n" +
                "@U :当这只精灵因为战斗把对战对手的精灵破坏时，从对战对手的牌组上面把10张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerStockAbility(new StockAbilityHarmony(2, new TargetFilter()));
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return !isOwnCard(cardIndex) && getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex cardIndex)
        {
            millDeck(getOpponent(), 10);
        }
    }
}
