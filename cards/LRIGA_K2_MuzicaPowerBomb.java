package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;

public final class LRIGA_K2_MuzicaPowerBomb extends Card {
    
    public LRIGA_K2_MuzicaPowerBomb()
    {
        setImageSets("WXDi-P03-034");
        
        setOriginalName("ムジカ／／パワーボム");
        setAltNames("ムジカパワーボム Mujika Pawaa Bomu");
        setDescription("jp",
                "@E：あなたのトラッシュからシグニを２枚まで対象とし、それらを能力を持たないシグニとして場に出す。ターン終了時、それらを場からトラッシュに置く。"
        );
        
        setName("en", "Muzica//Power Bomb");
        setDescription("en",
                "@E: Put up to two target SIGNI from your trash onto your field as SIGNI with no abilities. At end of turn, put them into their owner's trash."
        );
        
        setName("en_fan", "Muzica//Power Bomb");
        setDescription("en_fan",
                "@E: Target up to 2 SIGNI from your trash, and put them onto the field as SIGNI with no abilities. At the end of the turn, put them into the trash."
        );
        
		setName("zh_simplified", "穆希卡//力量炸弹");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把精灵2张最多作为对象，将这些作为不持有能力的精灵出场。回合结束时，将这些从场上放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
        }
        
        private void onEnterEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().playable().fromTrash());
            
            if(putOnField(data, Enter.NO_ABILITIES) > 0)
            {
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    DataTable<CardIndex> data2 = new TargetFilter().own().SIGNI().match(data).getExportedData();
                    trash(data2);
                });
            }
        }
    }
}
