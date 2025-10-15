package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_R2_TrapezeMediumTrap extends Card {

    public SIGNI_R2_TrapezeMediumTrap()
    {
        setImageSets("WX24-P3-070");

        setOriginalName("中罠　ヒノワクグリ");
        setAltNames("チュウビンヒノワクグリ Chuubin Hi no Wakuguri");
        setDescription("jp",
                "@A $T1 @[エナゾーンから＜トリック＞のシグニ１枚をトラッシュに置く]@：あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで【マジックボックス】としてあなたのシグニゾーンに設置し、残りを好きな順番でデッキの一番下に置く。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Trapeze, Medium Trap");
        setDescription("en",
                "@A $T1 @[Put 1 <<Trick>> SIGNI from your ener zone into the trash]@: Look at the top 3 cards of your deck. Put up to 1 card from among them onto 1 of your SIGNI zones as a [[Magic Box]], and put the rest on the bottom of your deck in any order." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "中罠 空中秋千");
        setDescription("zh_simplified", 
                "@A $T1 从能量区把<<トリック>>精灵1张放置到废弃区:从你的牌组上面看3张牌。从中把牌1张最多作为[[魔术箱]]在你的精灵区设置，剩下的任意顺序放置到牌组最下面。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
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

            ActionAbility act = registerActionAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.TRICK).fromEner()), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            look(3);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.ZONE).own().fromLooked()).get();
            putAsMagicBox(cardIndex);

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
