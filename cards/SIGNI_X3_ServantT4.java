package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityGuard;
import open.batoru.data.ability.stock.StockAbilityMultiEner;

public final class SIGNI_X3_ServantT4 extends Card {

    public SIGNI_X3_ServantT4()
    {
        setImageSets("WXK01-120", "WXK08-098", "SPK10-02-T");

        setOriginalName("サーバント　Ｔ４");
        setAltNames("サーバントティーフォー Saabanto Tii Foo Four");
        setDescription("jp",
                "~@" +
                "@C：【マルチエナ】" +
                "~#：あなたのトラッシュから＜精元＞のシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Servant T4");
        setDescription("en",
                "~@" +
                "@C: [[Multi Ener]]" +
                "~#Target 1 <<Origin Spirit>> SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "侍从 T4");
        setDescription("zh_simplified", 
                "[[防御]]（这张牌从手牌舍弃，能阻止一次因为分身的攻击造成的伤害）\n" +
                "@C :[[万花色]]（能量费用支付时，这张牌能作为任意颜色的1点支付）" +
                "~#从你的废弃区把<<精元>>精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.GUARD);

        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClass.ORIGIN);
        setLevel(3);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityGuard());
            registerStockAbility(new StockAbilityMultiEner());

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ORIGIN, CardSIGNIClassGeneration.SPIRIT).fromTrash()).get();
            addToHand(target);
        }
    }
}
