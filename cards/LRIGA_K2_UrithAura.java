package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class LRIGA_K2_UrithAura extends Card {

    public LRIGA_K2_UrithAura()
    {
        setImageSets("WXDi-P10-030");

        setOriginalName("ウリス・オーラ");
        setAltNames("ウリスオーラ Urisu Oora");
        setDescription("jp",
                "@E：あなたのルリグ１体を対象とし、ターン終了時まで、それは@>@U $T1：このルリグがアタックしたとき、あなたのシグニを２体まで場からトラッシュに置く。その後、あなたのトラッシュからこの方法でトラッシュに置いたシグニ１体につき##を持たないカード１枚を対象とし、それらをライフクロスに加える。@@を得る。"
        );

        setName("en", "Urith Aura");
        setDescription("en",
                "@E: Target LRIG on your field gains@>@U $T1: When this LRIG attacks, put up to two SIGNI on your field into their owner's trash. Then, add target card without ## from your trash to your Life Cloth for each SIGNI put into the trash this way.@@until end of turn."
        );
        
        setName("en_fan", "Urith Aura");
        setDescription("en_fan",
                "@E: Target your LRIG, and until end of turn, it gains:" +
                "@>@U $T1: When this LRIG attacks, put up to 2 of your SIGNI from the field into the trash. Then, for each SIGNI put into the trash this way, target 1 card without ## @[Life Burst]@ from your trash and add it to life cloth."
        );

		setName("zh_simplified", "乌莉丝·暗气");
        setDescription("zh_simplified", 
                "@E :你的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 当这只分身攻击时，你的精灵2只最多从场上放置到废弃区。然后，从你的废弃区把依据这个方法放置到废弃区的精灵的数量，每有1只就把不持有##的牌1张作为对象，将这些加入生命护甲。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().anyLRIG()).get();

            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.TRASH).own().SIGNI());
            int numTrashed = trash(data);
            
            if(numTrashed > 0)
            {
                numTrashed -= (int)data.stream().filter(cardIndex -> cardIndex.getLocation() != CardLocation.TRASH).count();
                
                for(int i=0;i<numTrashed;i++)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().not(new TargetFilter().lifeBurst()).fromTrash()).get();
                    addToLifeCloth(target);
                }
            }
        }
    }
}
