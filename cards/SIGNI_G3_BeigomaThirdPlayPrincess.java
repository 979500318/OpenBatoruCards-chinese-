package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.events.EventAttack;

public final class SIGNI_G3_BeigomaThirdPlayPrincess extends Card {

    public SIGNI_G3_BeigomaThirdPlayPrincess()
    {
        setImageSets("WX25-P2-058", "WX25-P2-058U");
        setLinkedImageSets("WX25-P2-026");

        setOriginalName("参ノ遊姫　ベイゴマ");
        setAltNames("サンノユウキベイゴマ San no Yuuki Beigoma");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、そのアタック終了時、あなたの場に《アイヤイ★クイーン》がいる場合、あなたのエナゾーンからレベル２以下の＜遊具＞のシグニ１枚を対象とし、%G %G %Xを支払ってもよい。そうした場合、それを場に出しこのシグニの場所と入れ替える。それの@E能力は発動しない。\n" +
                "@E：あなたの手札から＜遊具＞のシグニを２枚までエナゾーンに置く。"
        );

        setName("en", "Beigoma, Third Play Princess");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, at the end of that attack, if your LRIG is \"Aiyai★Queen\", target 1 level 2 or lower <<Playground Equipment>> SIGNI from your ener zone, and you may pay %G %G %X. If you do, exchange its position with this SIGNI. That SIGNI's @E abilities don't activate.\n" +
                "@E: Put up to 2 <<Playground Equipment>> SIGNI from your hand into the ener zone."
        );

		setName("zh_simplified", "叁之游姬 贝壳陀螺");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，那次攻击结束时，你的场上有《アイヤイ★クイーン》的场合，从你的能量区把等级2以下的<<遊具>>精灵1张作为对象，可以支付%G %G%X。这样做的场合，将其与场上的这只精灵的场所交换。其的@E能力不能发动。\n" +
                "@E :从你的手牌把<<遊具>>精灵2张最多放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            callDelayedEffect(((EventAttack)getEvent()).requestPostAttackTrigger(), () -> {
                if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("アイヤイ★クイーン"))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,2).withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromEner().playableAs(getCardIndex())).get();
                    
                    if(target != null && getCardIndex().isSIGNIOnField() && payEner(Cost.color(CardColor.GREEN, 2) + Cost.colorless(1)))
                    {
                        putInEner(getCardIndex());
                        if(target.getLocation() == CardLocation.ENER) putOnField(target, getCardIndex().getPreTransientLocation(), Enter.DONT_ACTIVATE);
                    }
                }
            });
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromHand());
            putInEner(data);
        }
    }
}
