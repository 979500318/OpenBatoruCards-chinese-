package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_LinXiangruWickedGeneral extends Card {

    public SIGNI_K2_LinXiangruWickedGeneral()
    {
        setImageSets("WX25-P2-106");

        setOriginalName("凶将　リンショウジョ");
        setAltNames("キョウショウリンショウジョ Kyoushou Rinshoujo");
        setDescription("jp",
                "@A #D @[エナゾーンから＜武勇＞のシグニ１枚をトラッシュに置く]@：あなたのトラッシュから＜武勇＞のシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Lin Xiangru, Wicked General");
        setDescription("en",
                "@A #D @[Put 1 <<Valor>> SIGNI from your ener zone into the trash]@: Target 1 <<Valor>> SIGNI from your trash, and put it onto the field." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "凶将 蔺相如");
        setDescription("zh_simplified", 
                "@A #D从能量区把<<武勇>>精灵1张放置到废弃区:从你的废弃区把<<武勇>>精灵1张作为对象，将其出场。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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

            registerActionAbility(new AbilityCostList(new DownCost(), new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.VALOR).fromEner())), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.VALOR).fromTrash().playable()).get();
            putOnField(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
