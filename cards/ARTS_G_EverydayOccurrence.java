package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

public final class ARTS_G_EverydayOccurrence extends Card {

    public ARTS_G_EverydayOccurrence()
    {
        setImageSets("WX24-P3-038");

        setOriginalName("日常茶飯");
        setAltNames("リラックスルーティン Rirakkusu Ruutin Relaxation Routine");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "$$2あなたのライフクロスが０枚の場合、あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );

        setName("en", "Everyday Occurrence");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 This turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "$$2 If you have 0 life cloth, shuffle your deck, and add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "日常茶饭");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "$$2 你的生命护甲在0张的场合，你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setModeChoice(1);
        }

        private void onARTSEff()
        {
            if(arts.getChosenModes() == 1)
            {
                ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXZoneWall(getOwner(),CardLocation.LIFE_CLOTH, "generic", new int[]{50,205,50}));
                
                blockNextDamage(record, null);
            } else if(getLifeClothCount(getOwner()) == 0)
            {
                shuffleDeck();
                addToLifeCloth(1);
            }
        }
    }
}
