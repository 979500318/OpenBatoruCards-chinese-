package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_DeusShield extends Card {
    
    public LRIGA_K2_DeusShield()
    {
        setImageSets("WXDi-D07-007");
        
        setOriginalName("デウスシールド");
        setAltNames("Deusu Shiirudo");
        setDescription("jp",
                "@E：このターン、次とその次にあなたがダメージを受ける場合、代わりにダメージを受けず、ターン終了時まで、このルリグは@>@U：ターン終了時、あなたのデッキの上からカードを５枚トラッシュに置く。@@を得る。\n" +
                "@E %K %X %X：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けず、ターン終了時まで、このルリグは@>@U：ターン終了時、あなたのデッキの上からカードを５枚トラッシュに置く。@@を得る。"
        );
        
        setName("en", "Deus Shield");
        setDescription("en",
                "@E: The next two times you would take damage this turn, instead you do not take that damage and this LRIG gains@>@U: At end of turn, put the top five cards of your deck into your trash.@@until end of turn. \n" +
                "@E %K %X %X: The next time you would take damage this turn, instead you do not take that damage and this LRIG gains@>@U: At end of turn, put the top five cards of your deck into your trash.@@until end of turn."
        );
        
        setName("en_fan", "Deus Shield");
        setDescription("en_fan",
                "@E: This turn, the next time you would be damaged and the next time after that, instead you aren't damaged, and until end of turn, this LRIG gains:" +
                "@>@U: At the end of the turn, put the top 5 cards of your deck into the trash.@@" +
                "@E %K %X %X: This turn, the next time you would be damaged, instead you aren't damaged, and until end of turn, this LRIG gains:" +
                "@>@U: At the end of the turn, put the top 5 cards of your deck into the trash."
        );
        
		setName("zh_simplified", "迪乌斯屏障");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次和再下一次你受到伤害的场合，作为替代，不会受到伤害，直到回合结束时为止，这只分身得到\n" +
                "@>@U :回合结束时，从你的牌组上面把5张牌放置到废弃区。@@\n" +
                "@E %K%X %X:这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害，直到回合结束时为止，这只分身得到\n" +
                "@>@U :回合结束时，从你的牌组上面把5张牌放置到废弃区。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.DEUS);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(2)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            Ability sourceAbility = getAbility();
            blockNextDamage(2, () -> attachAutoAbility(sourceAbility));
        }
        private void onEnterEff2()
        {
            Ability sourceAbility = getAbility();
            blockNextDamage(() -> attachAutoAbility(sourceAbility));
        }
        private void attachAutoAbility(Ability sourceAttachAbility)
        {
            AutoAbility attachAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachAbility(getCardIndex(), sourceAttachAbility, attachAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            millDeck(5);
        }
    }
}
