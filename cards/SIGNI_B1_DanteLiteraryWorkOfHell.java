package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_B1_DanteLiteraryWorkOfHell extends Card {

    public SIGNI_B1_DanteLiteraryWorkOfHell()
    {
        setImageSets("SPK01-02");

        setOriginalName("魔界の詩文　ダンテ");
        setAltNames("マカイノシブンダンテ Makai no Shibun Dante");
        setDescription("jp",
                "@E：あなたの手札からカードを２枚までこのシグニの下に置く。\n" +
                "@U：このシグニが場を離れたとき、あなたのトラッシュからこのカードの下にあったカードを２枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Dante, Literary Work of Hell");
        setDescription("en",
                "@E: Put up to 2 cards from your hand under this SIGNI.\n" +
                "@U: When this SIGNI leaves the field, target up to 2 cards from your trash that were under this SIGNI, and add them to your hand."
        );

		setName("zh_simplified", "魔界的诗文 但丁");
        setDescription("zh_simplified", 
                "@E :从你的手牌把牌2张最多放置到这只精灵的下面。\n" +
                "@U :当这只精灵离场时，从你的废弃区把这张牌的下面原有的牌2张最多作为对象，将这些加入手牌。\n"
        );

        setLRIGType(CardLRIGType.PIRULUK);
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(1000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().fromHand());
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }

        private DataTable<CardIndex> dataUnder;
        private ConditionState onAutoEffCond()
        {
            if(CardLocation.isSIGNI(EventMove.getDataMoveLocation())) return ConditionState.BAD;
            
            dataUnder = new TargetFilter().own().under(getCardIndex()).getExportedData();
            return ConditionState.OK;
        }
        private void onAutoEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter().own().fromTrash().match(dataUnder));
            addToHand(data);
        }
    }
}
