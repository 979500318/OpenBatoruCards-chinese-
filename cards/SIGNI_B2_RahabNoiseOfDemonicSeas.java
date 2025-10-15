package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B2_RahabNoiseOfDemonicSeas extends Card {

    public SIGNI_B2_RahabNoiseOfDemonicSeas()
    {
        setImageSets("WXK01-086");

        setOriginalName("魔海の騒音　ラハブ");
        setAltNames("マカイノソウオンラハブ Makai no Souon Rahabu");
        setDescription("jp",
                "@E @[手札を１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Rahab, Noise of Demonic Seas");
        setDescription("en",
                "@E @[Discard 1 card from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );

		setName("zh_simplified", "魔海的骚音 喇合");
        setDescription("zh_simplified", 
                "@E 手牌1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(1), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
    }
}
