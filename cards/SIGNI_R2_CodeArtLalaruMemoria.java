package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_R2_CodeArtLalaruMemoria extends Card {
    
    public SIGNI_R2_CodeArtLalaruMemoria()
    {
        setImageSets("WXDi-P06-057", "WXDi-P06-057P");
        
        setOriginalName("コードアート　ララ・ルー//メモリア");
        setAltNames("コードアートララルーメモリア Koodo Aato Rararuu Memoria");
        setDescription("jp",
                "=R あなたのレベル１のシグニ１体の上に置く\n\n" +
                "@C：このシグニは対戦相手の効果によって新たに能力を得られない。\n" +
                "@U：このシグニがアタックしたとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引く。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Lalaru//Memoria, Code: Art");
        setDescription("en",
                "=R Place on top of a level one SIGNI on your field. \n" +
                "@C: This SIGNI cannot gain new abilities by your opponent's effects.\n" +
                "@U: Whenever this SIGNI attacks, you may discard a card. If you do, draw a card." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Code Art Lalaru//Memoria");
        setDescription("en_fan",
                "=R Put on 1 of your level 1 SIGNI\n\n" +
                "@C: This SIGNI can't gain new abilities by your opponent's effects.\n" +
                "@U: Whenever this SIGNI attacks, you may discard 1 card from your hand. If you do, draw 1 card." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );
        
		setName("zh_simplified", "必杀代号 啦啦·噜//回忆");
        setDescription("zh_simplified", 
                "=R在你的等级1的精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@C :这只精灵不会因为对战对手的效果新得到能力。\n" +
                "@U :当这只精灵攻击时，可以把手牌1张舍弃。这样做的场合，抽1张牌。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withLevel(1));
            
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_ABILITY_BE_ATTACHED, this::onConstEffModRuleCheck));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility().getSourceAttachAbility() != null &&
                   !isOwnCard(data.getSourceAbility().getSourceAttachAbility().getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private void onAutoEff()
        {
            if(discard(0,1).get() != null)
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}
