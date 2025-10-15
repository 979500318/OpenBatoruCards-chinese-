package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_W2_BethorHolyAngel extends Card {

    public SIGNI_W2_BethorHolyAngel()
    {
        setImageSets("WX25-P1-067");

        setOriginalName("聖天　ベトール");
        setAltNames("セイテンベトール Seiten Betooru");
        setDescription("jp",
                "@E @[手札から＜天使＞のシグニを１枚捨てる]@：あなたのトラッシュに＜天使＞のシグニが５枚以上ある場合、対戦相手のレベル１のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Bethor, Holy Angel");
        setDescription("en",
                "@E @[Discard 1 <<Angel>> SIGNI from your hand]@: If there are 5 or more <<Angel>> SIGNI in your trash, target 1 of your opponent's level 1 SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "圣天 贝瑟尔");
        setDescription("zh_simplified", 
                "@E 从手牌把<<天使>>精灵1张舍弃:你的废弃区的<<天使>>精灵在5张以上的场合，对战对手的等级1的精灵1只作为对象，将其返回手牌。（费用支付后，计算<<天使>>的张数）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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

            registerEnterAbility(new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash().getValidTargetsCount() >= 5)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
                addToHand(target);
            }
        }
    }
}
