package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.events.EventAttack;

public final class SIGNI_G1_KatakataFirstPlay extends Card {

    public SIGNI_G1_KatakataFirstPlay()
    {
        setImageSets("WX25-P2-090");

        setOriginalName("壱ノ遊　カタカタ");
        setAltNames("イチノユウカタカタ Ichi no Yuu Katakata");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、そのアタック終了時、あなたのエナゾーンからレベル１の＜遊具＞のシグニ１枚を対象とし、場にあるこのシグニをエナゾーンに置いてもよい。そうした場合、それをダウン状態で場に出す。その@E能力は発動しない。"
        );

        setName("en", "Katakata, First Play");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, at the end of that attack, target 1 level 1 <<Playground Equipment>> SIGNI from your ener zone, and you may put this SIGNI from the field into the ener zone. If you do, put the targeted SIGNI onto the field downed. Its @E abilities don't activate."
        );

		setName("zh_simplified", "壹之游 滑梯小人");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，那次攻击结束时，从你的能量区把等级1的<<遊具>>精灵1张作为对象，可以把场上的这只精灵放置到能量区。这样做的场合，将其以横置状态出场。其的@E能力不能发动。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(3000);

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
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(1).withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromEner().playableAs(getCardIndex())).get();
                
                if(target != null && getCardIndex().isSIGNIOnField() && playerChoiceActivate())
                {
                    putInEner(getCardIndex());
                    putOnField(target, Enter.DOWNED | Enter.DONT_ACTIVATE);
                }
            });
        }
    }
}
