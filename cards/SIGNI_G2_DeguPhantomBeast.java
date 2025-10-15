package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G2_DeguPhantomBeast extends Card {
    
    public SIGNI_G2_DeguPhantomBeast()
    {
        setImageSets("WXDi-P03-078");
        
        setOriginalName("幻獣　デグー");
        setAltNames("コードメイズアクフク Genjuu Deguu");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのエナゾーンからこのシグニよりパワーの低い＜地獣＞のシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。"
        );
        
        setName("en", "Octodon, Phantom Terra Beast");
        setDescription("en",
                "@U: At the end of your turn, put target <<Terra Beast>> SIGNI in your Ener Zone with less power than this SIGNI onto your field. The @E abilities of SIGNI put onto your field this way do not activate."
        );
        
        setName("en_fan", "Degu, Phantom Beast");
        setDescription("en_fan",
                "@U: At the end of your turn, target 1 <<Earth Beast>> SIGNI from your ener zone with less power than this SIGNI, and put it onto the field. Its @E abilities don't activate."
        );
        
		setName("zh_simplified", "幻兽 八齿鼠");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，从你的能量区把比这只精灵的力量低的<<地獣>>精灵1张作为对象，将其出场。其的@E能力不能发动。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST).withPower(0, getPower().getValue()-1).fromEner().playable()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
    }
}
