package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ModifiableInteger;
import open.batoru.data.ValueByReference;
import open.batoru.data.ValueByReferenceOptions;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.ValueByReferenceModifier;

public final class PIECE_X_ParadiseUniverse extends Card {
    
    public PIECE_X_ParadiseUniverse()
    {
        setImageSets("WXDi-P05-004");
        
        setOriginalName("パラダイスウチュウ");
        setAltNames("Paradaisu Uchuu");
        setDescription("jp",
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@C：あなたの能力か効果１つによって、あなたのデッキかトラッシュにあるレベル３以下の＜宇宙＞のシグニのレベルを参照する場合、レベル１として扱ってもよい。"
        );
        
        setName("en", "Paradise Universe");
        setDescription("en",
                "You gain the following ability for the duration of the game.\n" +
                "@>@C: If one of your abilities or effects refers to a level three or less <<Cosmos>> SIGNI in your deck and trash, you may treat it as a level one."
        );
        
        setName("en_fan", "Paradise Universe");
        setDescription("en_fan",
                "During this game, you gain the following ability:" +
                "@>@C: If 1 of your abilities or effects would refer to a level 3 or lower <<Space>> SIGNI in your deck or trash, you may treat it as level 1."
        );
        
		setName("zh_simplified", "宇宙乐园");
        setDescription("zh_simplified", 
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@C 因为你的能力或效果1个，把你的牌组和废弃区的等级3以下的<<宇宙>>精灵的等级参照的场合，可以视作等级1。@@\n"
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
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(
                new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.SPACE).withLevel(0,3).fromLocation(CardLocation.DECK_MAIN, CardLocation.TRASH),
                new ValueByReferenceModifier<>(this::onAttachedConstEffModGetSample, new ValueByReference<>(this::onAttachedConstEffModByRefCond, new ValueByReferenceOptions<>(1)))
            );
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());
        }
        private ModifiableInteger onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getLevel();
        }
        private boolean onAttachedConstEffModByRefCond(CardIndex cardIndex, Ability sourceAbility)
        {
            return isOwnCard(sourceAbility.getSourceCardIndex());
        }
    }
}
