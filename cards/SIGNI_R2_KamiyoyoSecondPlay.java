package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_KamiyoyoSecondPlay extends Card {

    public SIGNI_R2_KamiyoyoSecondPlay()
    {
        setImageSets("WX24-P4-104");

        setOriginalName("弐ノ遊 カミヨーヨー");
        setAltNames("ニノユウカミヨーヨー Ni no Yuu Kamiyoyo");
        setDescription("jp",
                "@U：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、対戦相手のパワー7000以下のシグニ１体を対象とし、このシグニをバニッシュしてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Kamiyoyo, Second Play");
        setDescription("en",
                "@U: Whenever this SIGNI crushes 1 of your opponent's life cloth, target 1 of your opponent's SIGNI with power 7000 or less, and you may banish this SIGNI. If you do, banish it."
        );

		setName("zh_simplified", "贰之游 甩纸棒");
        setDescription("zh_simplified", 
                "@U :当这只精灵把对战对手的生命护甲1张击溃时，对战对手的力量7000以下的精灵1只作为对象，可以把这只精灵破坏。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,7000)).get();
            
            if(target != null && playerChoiceActivate() && banish(getCardIndex()))
            {
                banish(target);
            }
        }
    }
}
