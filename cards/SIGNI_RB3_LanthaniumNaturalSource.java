package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.events.EventAttack;

public final class SIGNI_RB3_LanthaniumNaturalSource extends Card {

    public SIGNI_RB3_LanthaniumNaturalSource()
    {
        setImageSets("WX24-P4-052");

        setOriginalName("羅原姫　Ｌａ");
        setAltNames("ラゲンヒメランタン Ragenhime Rantan");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、対戦相手の手札を１枚見ないで選び、捨てさせる。\n" +
                "@U：このシグニがアタックしたとき、そのアタック終了時、対戦相手のパワー8000以下のシグニ１体を対象とし、このシグニをバニッシュしてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Lanthanium, Natural Source");
        setDescription("en",
                "@U: When this SIGNI is banished, choose 1 card from your opponent's hand without looking, and discard it.\n" +
                "@U: Whenever this SIGNI attacks, at the end of that attack, target 1 of your opponent's SIGNI with power 8000 or less, and you may banish this SIGNI. If you do, banish it."
        );

		setName("zh_simplified", "罗原姬 La");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，不看对战对手的手牌选1张，舍弃。\n" +
                "@U :当这只精灵攻击时，那次攻击结束时，对战对手的力量8000以下的精灵1只作为对象，可以把这只精灵破坏。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED, CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private void onAutoEff1()
        {
            CardIndex cardIndex = playerChoiceHand().get();
            discard(cardIndex);
        }
        
        private void onAutoEff2()
        {
            callDelayedEffect(((EventAttack)getEvent()).requestPostAttackTrigger(), () -> {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                
                if(getCardIndex().isSIGNIOnField() && playerChoiceActivate() && banish(getCardIndex()))
                {
                    banish(target);
                }
            });
        }
    }
}
