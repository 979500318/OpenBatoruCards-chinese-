package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B4_SlimeQueenStickyFigureOfHell extends Card {

    public SIGNI_B4_SlimeQueenStickyFigureOfHell()
    {
        setImageSets("WDK02-012");

        setOriginalName("魔界の粘形　スライムクイーン");
        setAltNames("マカイノネンギョウスライムクイーン Makai no Nengyou Suraimu Kuiin");
        setDescription("jp",
                "@E @[手札を２枚捨てる]@：対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Slime Queen, Sticky Figure of Hell");
        setDescription("en",
                "@E @[Discard 2 cards from your hand]@: Target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "魔界的粘形 史莱姆女王");
        setDescription("zh_simplified", 
                "@E 手牌2张舍弃:对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(4);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
    }
}
