package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SIGNI_K2_DantalionWickedDevil extends Card {

    public SIGNI_K2_DantalionWickedDevil()
    {
        setImageSets("WX24-P3-091");

        setOriginalName("凶魔　ダンタリオン");
        setAltNames("キョウマダンタリオン Kyouma Dantarion");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の＜悪魔＞のシグニがある場合、あなたか対戦相手のデッキの上からカードを３枚トラッシュに置く。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Dantalion, Wicked Devil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another <<Devil>> SIGNI on your field, put the top 3 cards of your or your opponent's deck into the trash." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "凶魔 但他林");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有其他的<<悪魔>>精灵的场合，从你或对战对手的牌组上面把3张牌放置到废弃区。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 3);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
