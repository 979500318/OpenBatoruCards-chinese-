package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_B3_FenrirAzureDevilPrincess extends Card {
    
    public SIGNI_B3_FenrirAzureDevilPrincess()
    {
        setImageSets("WXDi-P01-038");
        
        setOriginalName("蒼魔姫　フェンリル");
        setAltNames("ソウマキフェンリル Soumeki Fenriru");
        setDescription("jp",
                "=T ＜うちゅうのはじまり＞\n" +
                "^U：あなたのアタックフェイズ開始時、対戦相手は手札を１枚捨てる。\n" +
                "@E：対戦相手のシグニを２体まで対象とし、それらを凍結する。"
        );
        
        setName("en", "Fenrir, Azure Evil Queen");
        setDescription("en",
                "=T <<UCHU NO HAJIMARI>>\n" +
                "^U: At the beginning of your attack phase, your opponent discards a card.\n" +
                "@E: Freeze up to two target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Fenrir, Azure Devil Princess");
        setDescription("en_fan",
                "=T <<Universe's Beginning>>\n" +
                "^U: At the beginning of your attack phase, your opponent discards 1 card from their hand.\n" +
                "@E: Target up to 2 of your opponent's SIGNI, and freeze them."
        );
        
		setName("zh_simplified", "苍魔姬 芬里尔");
        setDescription("zh_simplified", 
                "=T<<うちゅうのはじまり>>\n" +
                "^U:你的攻击阶段开始时，对战对手把手牌1张舍弃。\n" +
                "@E :对战对手的精灵2只最多作为对象，将这些冻结。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING) && isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FREEZE).OP().SIGNI());
            freeze(data);
        }
    }
}
