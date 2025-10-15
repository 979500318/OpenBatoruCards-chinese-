package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class PIECE_X_ApexClothedWarriors extends Card {
    
    public PIECE_X_ApexClothedWarriors()
    {
        setImageSets("WXDi-P03-003");
        
        setOriginalName("頂点布武");
        setAltNames("チョウテンフブ Chouten Fubu");
        setDescription("jp",
                "=Uあなたのセンタールリグがレベル２以上\n\n" +
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@C：あなたの中央のシグニゾーンにある＜武勇＞のシグニのパワーを＋1000し、そのシグニは@>@U：あなたのメインフェイズ以外でこのシグニがバニッシュされたとき、[[エナチャージ１]]をする。@@を得る。"
        );
        
        setName("en", "Apex Warriors");
        setDescription("en",
                "=U Your center LRIG is level two or more.\n\n" +
                "You gain the following ability for the duration of the game.\n" +
                "@>@C: <<Brave>> SIGNI in your center SIGNI Zone get +1000 power and gain@>@U: When this SIGNI is vanished outside of your Main Phase, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Apex-Clothed Warriors");
        setDescription("en_fan",
                "=U Your center LRIG is level 2 or higher\n\n" +
                "During this game, you gain the following ability:" +
                "@>@C: The <<Valor>> SIGNI in your center SIGNI zone gets +1000 power, and:" +
                "@>@U: When this SIGNI is banished other than during your main phase, [[Ener Charge 1]].@@"
        );
        
		setName("zh_simplified", "顶点布武");
        setDescription("zh_simplified", 
                "=U你的核心分身在等级2以上\n" +
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@C :你的中央的精灵区的<<武勇>>精灵的力量+1000，那只精灵得到\n" +
                "@>@U :当在你的主要阶段以外把这只精灵破坏时，[[能量填充1]]。@@\n" +
                "。@@\n"
        );

        setType(CardType.PIECE);
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
            
            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            ConstantAbilityShared attachedConst1 = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromLocation(CardLocation.SIGNI_CENTER).withClass(CardSIGNIClass.VALOR),
                new PowerModifier(1000)
            );
            attachPlayerAbility(getOwner(), attachedConst1, ChronoDuration.permanent());
            
            ConstantAbilityShared attachedConst2 = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromLocation(CardLocation.SIGNI_CENTER).withClass(CardSIGNIClass.VALOR),
                new AbilityGainModifier(this::onAttachedConstEffShared2ModGetSample)
            );
            attachPlayerAbility(getOwner(), attachedConst2, ChronoDuration.permanent());
        }
        private Ability onAttachedConstEffShared2ModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setNestedDescriptionOffset(1);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return !isOwnTurn() || getCurrentPhase() != GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(1);
        }
    }
}
