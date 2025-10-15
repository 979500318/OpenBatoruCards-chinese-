package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_SpottedSealWaterPhantom extends Card {

    public SIGNI_B1_SpottedSealWaterPhantom()
    {
        setImageSets("WX24-P3-073");

        setOriginalName("幻水　ゴマフ");
        setAltNames("ゲンスイゴマフ Gensui Gomafu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の＜水獣＞のシグニがある場合、カードを１枚引く。"
        );

        setName("en", "Spotted Seal, Water Phantom");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another <<Water Beast>> SIGNI on your field, draw 1 card."
        );

		setName("zh_simplified", "幻水 斑海豹");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有其他的<<水獣>>精灵的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(1);
        setPower(2000);

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
        }

        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.WATER_BEAST).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                draw(1);
            }
        }
    }
}
