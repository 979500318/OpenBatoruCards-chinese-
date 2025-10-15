package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_CriusHolyAngel extends Card {
    
    public SIGNI_W2_CriusHolyAngel()
    {
        setImageSets("WXDi-P05-047");
        
        setOriginalName("聖天　クレイオス");
        setAltNames("セイテンクレイオス Seiten Kureiosu");
        setDescription("jp",
                "@C：あなたのトラッシュに＜天使＞のシグニが５枚以上あるかぎり、このシグニのパワーは＋5000される。\n" +
                "@C：あなたのトラッシュに＜天使＞のシグニが１０枚以上あるかぎり、このシグニは@>@U：このシグニがアタックしたとき、カードを１枚引く。@@を得る。"
        );
        
        setName("en", "Crius, Blessed Angel");
        setDescription("en",
                "@C: As long as there are five or more <<Angel>> SIGNI in your trash, this SIGNI gets +5000 power.\n" +
                "@C: As long as there are ten or more <<Angel>> SIGNI in your trash, this SIGNI gains@>@U: Whenever this SIGNI attacks, draw a card."
        );
        
        setName("en_fan", "Crius, Holy Angel");
        setDescription("en_fan",
                "@C: As long as there are 5 or more <<Angel>> SIGNI in your trash, this SIGNI gets +5000 power.\n" +
                "@C: As long as there are 10 or more <<Angel>> SIGNI in your trash, this SIGNI gains:" +
                "@>@U: Whenever this SIGNI attacks, draw 1 card."
        );
        
		setName("zh_simplified", "圣天 克瑞斯");
        setDescription("zh_simplified", 
                "@C :你的废弃区的<<天使>>精灵在5张以上时，这只精灵的力量+5000。\n" +
                "@C :你的废弃区的<<天使>>精灵在10张以上时，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，抽1张牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(5000));
            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEffModGetSample));
        }
        
        private ConditionState onConstEff1Cond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash().getValidTargetsCount() >= 5 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEff2Cond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash().getValidTargetsCount() >= 10 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            draw(1);
        }
    }
}
