package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.events.EventAttack;

public final class SIGNI_G2_HulaHoopSecondPlay extends Card {

    public SIGNI_G2_HulaHoopSecondPlay()
    {
        setImageSets("WX25-P2-093", "SPDi45-04","SPDi45-04P");

        setOriginalName("弍ノ遊　フラフープ");
        setAltNames("ニノユウフラフープ Ni no Yuu Furafuupu Hulahoop");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、そのアタック終了時、あなたのエナゾーンからレベル２以下の＜遊具＞のシグニ１枚を対象とし、場にあるこのシグニをエナゾーンに置いてもよい。そうした場合、そのシグニをダウン状態で場に出す。その@E能力は発動しない。"
        );

        setName("en", "Hula Hoop, Second Play");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, at the end of that attack, target 1 level 2 or lower <<Playground Equipment>> SIGNI from your ener zone, and you may put this SIGNI from the field into the ener zone. If you do, put the targeted SIGNI onto the field downed. Its @E abilities don't activate."
        );

		setName("zh_simplified", "贰之游 呼啦圈");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，那次攻击结束时，从你的能量区把等级2以下的<<遊具>>精灵1张作为对象，可以把场上的这只精灵放置到能量区。这样做的场合，将其以横置状态出场。其的@E能力不能发动。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private void onAutoEff()
        {
            callDelayedEffect(((EventAttack)getEvent()).requestPostAttackTrigger(), () -> {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,2).withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromEner().playableAs(getCardIndex())).get();
                
                if(target != null && getCardIndex().isSIGNIOnField() && playerChoiceActivate())
                {
                    putInEner(getCardIndex());
                    putOnField(target, Enter.DOWNED | Enter.DONT_ACTIVATE);
                }
            });
        }
    }
}
