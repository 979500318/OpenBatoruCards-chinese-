package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B2_CodeArtJUvenile extends Card {

    public SIGNI_B2_CodeArtJUvenile()
    {
        setImageSets("WX24-D3-15");

        setOriginalName("コードアート　Jユブナイル");
        setAltNames("コードアートジェイユブナイル Koodo Aato Jei Yubenairu");
        setDescription("jp",
                "@E @[手札から青のカードを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。対戦相手の手札が２枚以下の場合、代わりにターン終了時まで、それのパワーを－5000する。"
        );

        setName("en", "Code Art J Uvenile");
        setDescription("en",
                "@E @[Discard 1 blue card from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power. If there are 2 or less cards in your opponent's hand, instead, until end of turn, it gets --5000 power."
        );

		setName("zh_simplified", "必杀代号 打机王");
        setDescription("zh_simplified", 
                "@E 从手牌把蓝色的牌1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。对战对手的手牌在2张以下的场合，作为替代，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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

            registerEnterAbility(new DiscardCost(new TargetFilter().withColor(CardColor.BLUE)), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, getHandCount(getOpponent()) > 2 ? -3000 : -5000, ChronoDuration.turnEnd());
        }
    }
}
