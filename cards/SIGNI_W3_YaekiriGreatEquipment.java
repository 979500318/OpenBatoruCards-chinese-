package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_W3_YaekiriGreatEquipment extends Card {
    
    public SIGNI_W3_YaekiriGreatEquipment()
    {
        setImageSets("WXDi-P02-035");
        
        setOriginalName("大装　ヤエキリ");
        setAltNames("タイソウヤエキリ Taisou Yaekiri");
        setDescription("jp",
                "=H 赤のルリグ１体\n\n" +
                "@U $T1：あなたが[[ガード]]したとき、カードを１枚引く。\n" +
                "@U：このシグニがアタックしたとき、あなたのトラッシュから#Gを持つシグニ１枚を対象とし、%W %Xを支払ってもよい。そうした場合、それを手札に加える。"
        );
        
        setName("en", "Yaekiri, Full Armed");
        setDescription("en",
                "=H One red LRIG\n\n" +
                "@U $T1: When you [[Guard]], draw a card.\n" +
                "@U: Whenever this SIGNI attacks, you may pay %W %X. If you do, add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Yaekiri, Great Equipment");
        setDescription("en_fan",
                "=H 1 red LRIG\n\n" +
                "@U $T1: When you [[Guard]], draw 1 card.\n" +
                "@U: Whenever this SIGNI attacks, target 1 #G @[Guard]@ SIGNI from your trash, and you may pay %W %X. If you do, add it to your hand."
        );
        
		setName("zh_simplified", "大装 八重弦");
        setDescription("zh_simplified", 
                "=H红色的分身1只（当这只精灵出场时，如果不把你的竖直状态的红色的分身1只#D，那么将此牌#D）\n" +
                "@U $T1 :当你[[防御]]时，抽1张牌。\n" +
                "@U 当这只精灵攻击时，从你的废弃区把持有#G的精灵1张作为对象，可以支付%W%X。这样做的场合，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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
            
            registerStockAbility(new StockAbilityHarmony(1, new TargetFilter().withColor(CardColor.RED)));
            
            AutoAbility auto = registerAutoAbility(GameEventId.GUARD, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            draw(1);
        }
        
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            
            if(target != null && payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)))
            {
                addToHand(target);
            }
        }
    }
}
