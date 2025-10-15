package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_BuffaPhantomBeast extends Card {
    
    public SIGNI_R1_BuffaPhantomBeast()
    {
        setImageSets("WXDi-P05-055");
        setLinkedImageSets("WXDi-P02-039");
        
        setOriginalName("幻獣　バーファ");
        setAltNames("ゲンジュウバーファ Genjuu Baafa");
        setDescription("jp",
                "@C：あなたの場に《幻獣神　バッファロー》があるかぎり、このシグニのパワーは＋7000される。\n" +
                "@E：ターン終了時まで、このシグニのパワーをあなたの場にいるルリグのレベルの合計１につき＋1000する。"
        );
        
        setName("en", "Buffa, Phantom Beast");
        setDescription("en",
                "@C: As long as there is \"Buffalo, Phantom Terra Beast God\" on your field, this SIGNI gets +7000 power.\n" +
                "@E: This SIGNI gets +1000 power for every level of each of LRIG on your field until end of turn."
        );
        
        setName("en_fan", "Buffa, Phantom Beast");
        setDescription("en_fan",
                "@C: As long as there is a \"Buffalo, Phantom Beast Deity\" on your field, this SIGNI gets +7000 power.\n" +
                "@E: Until end of turn, this SIGNI gets +1000 power for each level of your LRIGs on the field."
        );
        
		setName("zh_simplified", "幻兽 侏儒水牛");
        setDescription("zh_simplified", 
                "@C :你的场上有《幻獣神　バッファロー》时，这只精灵的力量+7000。\n" +
                "@E :直到回合结束时为止，这只精灵的力量依据你的场上的分身的等级的合计的数量，每有1级就+1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(7000));
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withName("幻獣神　バッファロー").getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            int sumLevels = new TargetFilter().own().anyLRIG().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum();
            gainPower(getCardIndex(), 1000 * sumLevels, ChronoDuration.turnEnd());
        }
    }
}
