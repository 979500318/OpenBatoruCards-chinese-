package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_BonsaiNaturalPlant extends Card {

    public SIGNI_G1_BonsaiNaturalPlant()
    {
        setImageSets("WX24-P2-081");

        setOriginalName("羅植　ボンサイ");
        setAltNames("ラショクボンサイ Rashoku Bonsai");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の＜植物＞のシグニがある場合、【エナチャージ１】をする。"
        );

        setName("en", "Bonsai, Natural Plant");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another <<Plant>> SIGNI on your field, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "罗植 盆栽");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有其他的<<植物>>精灵的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
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
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PLANT).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                enerCharge(1);
            }
        }
    }
}
