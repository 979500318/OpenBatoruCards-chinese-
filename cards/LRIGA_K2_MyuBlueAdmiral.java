package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.AbilityGain;

public final class LRIGA_K2_MyuBlueAdmiral extends Card {

    public LRIGA_K2_MyuBlueAdmiral()
    {
        setImageSets("WXDi-P13-043");

        setOriginalName("ミュウ　－　ルリタテハ");
        setAltNames("ミュウルリタテハ Myuu Ruritateha");
        setDescription("jp",
                "@E：対戦相手のシグニを２体まで対象とし、次のあなたのターン終了時まで、それらは能力を失い、それらのパワーを－5000する。"
        );

        setName("en", "Myu - Blue Admiral");
        setDescription("en",
                "@E: Up to two target SIGNI on your opponent's field lose their abilities and get --5000 power until the end of your next end phase."
        );
        
        setName("en_fan", "Myu - Blue Admiral");
        setDescription("en_fan",
                "@E: Target up to 2 of your opponent's SIGNI, and until the end of your next turn, they lose their abilities, and get --5000 power."
        );

		setName("zh_simplified", "缪-琉璃蛺蝶");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵2只最多作为对象，直到下一个你的回合结束时为止，这些的能力失去，这些的力量-5000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.MUTE).OP().SIGNI());
            if(data.get() != null)
            {
                disableAllAbilities(data, AbilityGain.ALLOW, ChronoDuration.nextTurnEnd(getOwner()));
                gainPower(data, -5000, ChronoDuration.nextTurnEnd(getOwner()).repeat(isOwnTurn() ? 2 : 1));
            }
        }
    }
}
