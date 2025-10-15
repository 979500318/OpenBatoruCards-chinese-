package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_R2_BedivereCrimsonGeneral extends Card {
    
    public SIGNI_R2_BedivereCrimsonGeneral()
    {
        setImageSets("WXDi-P04-057");
        
        setOriginalName("紅将　ベディヴィエール");
        setAltNames("コウショウベディヴィエール Koushou Bediveeru");
        setDescription("jp",
                "@C：このシグニは中央のシグニゾーンにあるかぎり、@>@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。\n" +
                "$$2対戦相手のパワー2000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );
        
        setName("en", "Bedivere, Crimson General");
        setDescription("en",
                "@C: As long as this SIGNI is in your center SIGNI Zone, it gains@>@U: Whenever this SIGNI attacks, choose one of the following.\n" +
                "$$1 Put target card from your opponent's Ener Zone that does not share a color with your opponent's center LRIG into their trash.\n" +
                "$$2 Vanish target SIGNI on your opponent's field with power 2000 or less."
        );
        
        setName("en_fan", "Bedivere, Crimson General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is in your center SIGNI zone, it gains:" +
                "@>@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 2000 or less, and banish it."
        );
        
		setName("zh_simplified", "红将 贝狄威尔");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，得到@>@U ：当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。\n" +
                "$$2 对战对手的力量2000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
                trash(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                banish(target);
            }
        }
    }
}
